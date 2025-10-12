using System.ComponentModel.DataAnnotations;

namespace Noitcua.Models
{
    public class ResetPasswordModel
    {
        [Required]
        [MinLength(8)]
        [MaxLength(50)]
        public string Password { get; set; }
        [Required]
        [MinLength(8)]
        [MaxLength(50)]
        public string ConfirmPassword { get; set;}
    }
}
