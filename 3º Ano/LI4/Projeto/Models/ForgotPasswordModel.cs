using System.ComponentModel.DataAnnotations;

namespace Noitcua.Models;
public class ForgotPasswordModel
{
    [Required]
    [EmailAddress]
    [MaxLength(50)]
    public string Email { get; set; }

    [Required]
    [MaxLength(20)]
    public string Handle { get; set; }

    [Required]
    [MinLength(8)]
    [MaxLength(50)]
    public string Password { get; set; }

    [Required]
    [MinLength(8)]
    [MaxLength(50)]
    public string ConfirmPassword { get; set; }
}

